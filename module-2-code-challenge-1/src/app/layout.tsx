import React from "react";
import Wrapper from "@/component/Wrapper";
import {Metadata} from "next";

export const metadata: Metadata = {
    title: "Company",
    description: "Company App",
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
        <Wrapper>
            {children}
        </Wrapper>
        </body>
        </html>
    );
}
