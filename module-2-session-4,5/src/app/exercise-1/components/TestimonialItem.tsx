import React from "react";
import Image from "next/image";

type TestimonialItemProps = {
    quote: string;
    author: string;
    position: string;
    avatarSrc: string;
};

const TestimonialItem: React.FC<TestimonialItemProps> = (
    {
        quote,
        author,
        position,
        avatarSrc,
    }) => {
    return (
        <div className="mb-[4rem]">
            <p className="text-[1.6875rem] text-[#3C3D3E] font-normal leading-[2.375rem]">
                {`"${quote}"`}
            </p>
            <div className="flex items-center gap-[0.75rem] mt-[1.75rem]">
                <Image
                    src={avatarSrc}
                    alt={`${author}'s avatar`}
                    width={64}
                    height={64}
                    className="rounded-full"
                />
                <div>
                    <p className="text-[1.125rem] text-[#0B0C0E] font-medium">{author}</p>
                    <p className="text-[0.875rem] text-[#0B0C0E] font-normal">
                        {position}
                    </p>
                </div>
            </div>
        </div>
    );
};

export default TestimonialItem;
