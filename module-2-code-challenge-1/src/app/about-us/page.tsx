import React from "react";
import Team from "@/component/Team";
import AboutUs from "@/component/AboutUs";


export default function Page() {

    return (
        <div className="container mx-auto p-4">
            <div className="mt-3"/>
            <AboutUs/>
            <div className="mt-6"/>
            <Team/>
            <div className="mt-12"/>
        </div>
    );
}
