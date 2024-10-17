import React from "react";

export default function AboutUs() {
    return (
        <div className="container mx-auto p-4 flex flex-col items-center gap-12">
            <h1 className="text-5xl font-bold">About Us</h1>
            <p className="text-xl text-center">Tagline lorem Ipsum is simply dummy text of the printing and typesetting
                industry.</p>
            <div className="flex justify-center flex-col md:flex-row gap-12">
                <div>
                    <h2 className="text-2xl font-semibold">History</h2>
                    <p>
                        {`Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.`}
                    </p>
                </div>
                <div>
                    <h2 className="text-2xl font-semibold">Culture</h2>
                    <p>
                        {`Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.`}
                    </p>
                </div>
                <div>
                    <h2 className="text-2xl font-semibold">Values</h2>
                    <p>
                        {`Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.`}
                    </p>
                </div>
            </div>
        </div>
    )
}
