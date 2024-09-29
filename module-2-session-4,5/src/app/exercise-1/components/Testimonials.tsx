import React from "react";
import TestimonialItem from "./TestimonialItem";

const testimonials = [
    {
        quote:
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        author: "Ayush Raj",
        position: "VP of Marketing @ Webflow",
        avatarSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/17fad1dcaedab10cca8aec1d7a7238dbf7f4783205677c77af1bd04872d8606c?apiKey=e946ad7206da4373a899cf38e79aca51&",
    },
    {
        quote:
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        author: "Alex Cattoni",
        position: "Founder @ CopyPossy",
        avatarSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/eb700bf1ebb1fa7b79b75b25da7658f0dcc34001bfd336c7e02c2ed70f4e2228?apiKey=e946ad7206da4373a899cf38e79aca51&",
    },
];

const Testimonials: React.FC = () => {
    return (
        <section className="py-[10rem]">
            <div className="container mx-auto flex">
                <h2 className="w-2/5 text-[2.5rem] text-[#0B0C0E] font-medium">
                    Testimonials
                </h2>
                <div className="w-full">
                    {testimonials.map((testimonial, index) => (
                        <TestimonialItem key={index} {...testimonial} />
                    ))}
                </div>
            </div>
        </section>
    );
};

export default Testimonials;
