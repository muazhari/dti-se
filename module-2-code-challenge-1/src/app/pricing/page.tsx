import React from "react";
import Pricing from "@/component/Pricing";
import Testimony from "@/component/Testimony";


export default function Page() {

    return (
        <div className="container mx-auto p-4">
            <div className="mt-3"/>
            <Pricing/>
            <div className="mt-6"/>
            <Testimony/>
            <div className="mt-12"/>
        </div>
    );
}
