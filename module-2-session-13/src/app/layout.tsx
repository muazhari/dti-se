'use client'
import "./globals.scss";
import React from "react";
import {persistor, store} from "@/app/exercise-1/stores";
import {Provider} from "react-redux";
import {PersistGate} from "redux-persist/integration/react";
import {ThemeModeScript} from "flowbite-react";

export default function RootLayout(
    {
        children,
    }: Readonly<{
        children: React.ReactNode;
    }>
) {
    return (
        <html lang="en">
        <head>
            <ThemeModeScript/>
            <title/>
        </head>
        <body>
        <Provider store={store}>
            <PersistGate casdadasd persistor={persistor}>
                {children}
            </PersistGate>
        </Provider>
        </body>
        </html>
    );
}
