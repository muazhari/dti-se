import {createSlice} from "@reduxjs/toolkit";

export const landingSlice = createSlice({
    name: 'landing',
    initialState: {
        isShowSidebar: false,
    },
    reducers: {
        switchSidebar: (state) => {
            state.isShowSidebar = !state.isShowSidebar;
        },
    },
});
