import React from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";


type LayoutProps = {
    children: React.ReactNode;
};

const Layout: React.FC<LayoutProps> = ({children}) => {
    return (
        <>
            <Header/>
            {children}
            <Footer/>
        </>
    );
};

export default Layout;
