import React from "react";

const About: React.FC = () => {
    return (
        <section id="about" className="flex flex-col">
            <div className="container mx-auto flex justify-between items-start pt-[2rem] pb-[10rem]">
                <h2 className="w-2/5 text-[2.5rem] text-[#0B0C0E] font-medium">About</h2>
                <p className="w-full text-[1.6875rem] text-[#3C3D3E] font-normal leading-[2.375rem]">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                    eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
                    minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat. Duis aute irure dolor in
                    reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                    pariatur.
                </p>
            </div>
        </section>
    );
};

export default About;
