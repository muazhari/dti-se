import React from "react";
import AboutUs from "@/component/AboutUs";
import Pricing from "@/component/Pricing";
import Team from "@/component/Team";
import Testimony from "@/component/Testimony";
import Hero from "@/component/Hero";


export default function Page() {

    return (
        <>
            <Hero/>
            <div className="mt-12"/>
            <AboutUs/>
            <div className="mt-12"/>
            <Team/>
            <div className="mt-12"/>
            <Pricing/>
            <div className="mt-12"/>
            <Testimony/>
            <div className="mt-12"/>
        </>
    );
}
