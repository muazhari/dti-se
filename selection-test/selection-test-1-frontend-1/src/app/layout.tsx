import RootProvider from "@/src/components/RootProvider";
import type {Metadata} from "next";
import "./globals.scss";
import Header from "@/src/components/Header";
import Modal from "@/src/components/Modal";
import Footer from "../components/Footer";

export const metadata: Metadata = {
    title: "App",
    description: "selection-test-1-frontend-1",
};

export default function Layout(
    {
        children,
    }: Readonly<{
        children: React.ReactNode;
    }>
) {
    return (
        <html lang="en">
        <body>
        <RootProvider>
            <Modal/>
            <Header/>
            {children}
            <Footer/>
        </RootProvider>
        </body>
        </html>
    );
}