'use client'
import React from "react";

export default function Pricing() {
    const [plan, setPlan] = React.useState([
        {
            name: "Free",
            features: ["Feature 1", "Feature 2", "Feature 3", "Feature 4", "Feature 5"],
            price: "$0"
        },
        {
            name: "Plus",
            features: ["Feature 1", "Feature 2", "Feature 3", "Feature 4", "Feature 5"],
            price: "$10"
        },
        {
            name: "Pro",
            features: ["Feature 1", "Feature 2", "Feature 3", "Feature 4", "Feature 5"],
            price: "$50"
        }
    ])


    return (
        <div className="container mx-auto p-4 flex flex-col items-center gap-12">
            <h1 className="text-5xl font-bold">Products</h1>
            <div className="flex gap-6 md:flex-row flex-col">
                {plan.map((item, index) => (
                    <div key={index}
                         className="flex-1 flex flex-col w-[20rem] h-auto bg-gray-100 p-8 rounded-xl border shadow-xl">
                        <h2 className="text-2xl mb-4 font-semibold">{item.name}</h2>
                        <ol className="[&>li]:mb-4 list-disc list-inside text-l mb-4">
                            {item.features.map((item, index) => (
                                <li key={index}>{item}</li>
                            ))}
                        </ol>
                        <div className="text-xl mb-8">
                            {item.price} / month
                        </div>
                        <button className="bg-gray-500 text-white p-2 rounded-full border shadow-xl">Start Now</button>
                    </div>
                ))}
            </div>
        </div>
    )
}
