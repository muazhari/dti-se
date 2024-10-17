'use client'
import "./globals.scss";
import 'swiper/scss';
import 'swiper/scss/pagination';
import React from "react";
import {persistor, store} from "@/store";
import {Provider} from "react-redux";
import {PersistGate} from "redux-persist/integration/react";
import {NextUIProvider} from "@nextui-org/react";
import Header from "@/component/Header";
import Footer from "@/component/Footer";


export default function Layout(
    {
        children,
    }: Readonly<{
        children: React.ReactNode;
    }>
) {
    return (
        <html lang="en">
        <head>
            <title></title>
        </head>
        <body>
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <NextUIProvider>
                    <Header/>
                    <div className="min-h-[100vh]">
                        {children}
                    </div>
                    <Footer/>
                </NextUIProvider>
            </PersistGate>
        </Provider>
        </body>
        </html>
    );
}
