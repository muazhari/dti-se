import {combineReducers} from "redux";
import {configureStore} from "@reduxjs/toolkit";
import {persistReducer, persistStore} from "redux-persist";
import {rootSlice} from "@/store/slice";
import {FLUSH, PAUSE, PERSIST, PURGE, REGISTER, REHYDRATE} from "redux-persist/es/constants";
import createWebStorage from "redux-persist/es/storage/createWebStorage";
import {userApi} from "@/store/api";
import {setupListeners} from "@reduxjs/toolkit/query";


const rootReducer = combineReducers({
    [rootSlice.name]: rootSlice.reducer,
    [userApi.reducerPath]: userApi.reducer,
})

const createNoopStorage = () => {
    return {
        getItem(_key: string) {
            return Promise.resolve(null)
        },
        setItem(_key: string, value: string) {
            return Promise.resolve(value)
        },
        removeItem(_key: string) {
            return Promise.resolve()
        },
    }
}

const storage = typeof window !== 'undefined' ? createWebStorage('local') : createNoopStorage()
const persistedReducer = persistReducer({
        key: "persist",
        whitelist: [],
        storage,
    },
    rootReducer
);

export const store = configureStore({
    reducer: persistedReducer,
    devTools: process.env.NODE_ENV !== 'production',
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: {
            ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
        },
    }).concat(userApi.middleware),
})

setupListeners(store.dispatch)

export const persistor = persistStore(store)

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
