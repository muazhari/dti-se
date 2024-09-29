import React from "react";
import Image from "next/image";

interface WebsiteCardProps {
    src: string;
    alt: string;
}

const VisitWebsiteButton: React.FC = () => {
    return (
        <button
            className="absolute z-10 gap-2 py-3 px-4 font-medium bg-white rounded">
            <div className="flex items-center gap-2">
                <div>Visit the website</div>
                <div className="relative w-5 h-5">
                    <Image
                        src="https://cdn.builder.io/api/v1/image/assets/TEMP/a45e3e561b2bafe1500c3f12b1b25fbd3c1cdc130ba1d4737adf9adb4230c23d?apiKey=e946ad7206da4373a899cf38e79aca51&"
                        alt="Visit website icon"
                        fill
                        className="object-contain"
                    />
                </div>
            </div>
        </button>
    );
};


const WebsiteCard: React.FC<WebsiteCardProps> = ({src, alt}) => {
    return (
        <div
            className="flex flex-col justify-center px-12 py-14 rounded bg-slate-300 w-full h-full"
        >
            <div className="relative w-full h-full">
                <VisitWebsiteButton/>
                <Image
                    src={src}
                    alt={alt}
                    fill
                    className="object-contain"
                />
            </div>
        </div>
    );
};

export default WebsiteCard;
