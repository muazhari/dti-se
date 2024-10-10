import React from "react";
import Image from "next/image";

type WorkItemProps = {
    title: string;
    categories: string[];
    iconSrc: string;
    darkMode?: boolean;
};

const WorkItem: React.FC<WorkItemProps> = (
    {
        title,
        categories,
        iconSrc,
        darkMode = false,
    }
) => {
    return (
        <div className="mb-[2rem]">
            <div className="flex justify-between items-center">
                <div>
                    <h3 className="text-[3.75rem] text-[#0B0C0E] font-normal">{title}</h3>
                    <div
                        className="flex items-center gap-[0.375rem] text-[1.125rem] text-[#3C3D3E] font-normal mt-[1.125rem]">
                        {categories.map((category, index) => (
                            <React.Fragment key={index}>
                                {index > 0 && (
                                    <span>-</span>
                                )}
                                <span>{category}</span>
                            </React.Fragment>
                        ))}
                    </div>
                </div>
                <button
                    className={`relative w-[5.375rem] h-[5.375rem] rounded-[11.25rem] border border-[#3C3D3E] ${
                        darkMode ? "bg-[#0B0C0E]" : ""
                    }`}
                >
                    <Image
                        src={iconSrc}
                        alt=""
                        fill
                        className="object-cover"
                    />
                </button>
            </div>
            <div className="h-[0.125rem] bg-[#C7D0D9] mt-[2rem]"/>
        </div>
    );
};

export default WorkItem;
