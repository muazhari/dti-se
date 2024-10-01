import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import ListPage from "./pages/ListPage/index.tsx";
import PokeDetail from "./pages/PokeDetail/index.tsx";
import {Provider} from "react-redux";
import {persistor, store} from "./app/store.ts";
import {NextUIProvider} from "@nextui-org/react";
import {PersistGate} from "redux-persist/integration/react";

const router = createBrowserRouter([
    {
        path: "/",
        element: <ListPage/>,
    },
    {
        path: "/detail/:name",
        element: <PokeDetail/>,
    },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
    <NextUIProvider>
        <Provider store={store}>
            <PersistGate persistor={persistor}>
                <RouterProvider router={router}/>
            </PersistGate>
        </Provider>
    </NextUIProvider>
);
