'use client'
import React from "react";
import Image from "next/image";

export default function Testimony() {
    const [testimony, setTestimony] = React.useState([
        {
            name: "Andy",
            role: "CEO",
            company: "Company 1",
            summary: "Lorem ipsum",
            testimonial: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in turpis nec odio ultricies tristique. In hac habitasse platea dictumst."
        },
        {
            name: "Alice",
            role: "CTO",
            company: "Company 2",
            summary: "Lorem ipsum",
            testimonial: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in turpis nec odio ultricies tristique. In hac habitasse platea dictumst."
        },
        {
            name: "Adam",
            role: "CFO",
            company: "Company 3",
            summary: "Lorem ipsum",
            testimonial: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in turpis nec odio ultricies tristique. In hac habitasse platea dictumst."
        }
    ])

    return (
        <div className="container mx-auto p-4 flex flex-col items-center gap-12">
            <h1 className="text-5xl font-bold">Testimonials</h1>
            <div className="flex gap-6 md:flex-row flex-col">
                {testimony.map((item, index) => (
                    <div
                        key={index}
                        className="relative flex-1 flex flex-col w-[20rem] h-auto bg-gray-100 p-8 rounded-xl gap-8 border shadow-xl"
                    >
                        <Image
                            src={`https://placehold.co/100x300?text=user${index}`}
                            alt="user"
                            layout="fill"
                            objectFit="cover"
                            className="rounded-xl z-0"
                        />
                        <h2 className="z-10 text-2xl mb-[12rem] font-semibold">{item.summary}</h2>
                        <p className="z-10 text-l">
                            {item.testimonial}
                        </p>
                        <div className="z-10 text-xl">
                            {item.name}, {item.role} - {item.company}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}
