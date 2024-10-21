'use client'
import React from "react";
import {persistor, store} from "@/store";
import {PersistGate} from "redux-persist/integration/react";
import {NextUIProvider} from "@nextui-org/react";
import {Provider} from "react-redux";
import "@/app/globals.scss";
import 'swiper/scss';
import 'swiper/scss/pagination';
import Header from "@/component/Header";
import Footer from "@/component/Footer";

export default function Wrapper(
    {
        children,
    }: Readonly<{
        children: React.ReactNode;
    }>
) {
    return (
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
    )
}
