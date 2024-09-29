import React from "react";
import WorkItem from "./WorkItem";
import WebsiteCard from "@/app/exercise-1/components/WebsiteCard";
import {Carousel} from "flowbite-react";

const workItems = [
    {
        title: "Decodable.co",
        categories: ["Brand Design", "Webflow Development", "Web Design"],
        iconSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/99486ffacf933a717b254397efa3da40dc540fba236e867dd48971fddc924111?apiKey=e946ad7206da4373a899cf38e79aca51&",
    },
    {
        title: "Gofirefly.io",
        categories: ["Brand Design", "Webflow Development", "Web Design"],
        iconSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/14d31cf7168ab5ebd2cc3bbf56313a974476b806e779edf39f4a9abf52140fe1?apiKey=e946ad7206da4373a899cf38e79aca51&",
        darkMode: true,
    },
    {
        title: "Blinkops.com",
        categories: ["Brand Design", "Webflow Development", "Web Design"],
        iconSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/99486ffacf933a717b254397efa3da40dc540fba236e867dd48971fddc924111?apiKey=e946ad7206da4373a899cf38e79aca51&",
    },
    {
        title: "Withkanvas.com",
        categories: ["Brand Design", "Webflow Development", "Web Design"],
        iconSrc:
            "https://cdn.builder.io/api/v1/image/assets/TEMP/99486ffacf933a717b254397efa3da40dc540fba236e867dd48971fddc924111?apiKey=e946ad7206da4373a899cf38e79aca51&",
    },
];
const websiteImages = [
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/535a7818e1502e1803dfffd8868419ff16b0ecbc2e3243ad639be74014b92bce?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 1"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/4785e8d5124720476cfaed023ea1b4ade4ecc5db871067f756e12fded247e5c4?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 2"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/1839bea4e86af43bcc6bf2f967df0fb47640981a0ad3309eb87cfd2dc8188cea?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 3"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/5f0c7aa76e58324ad6768ac1dc5c3ae01341a62c7b9cfbb35275b4d54dad2fbb?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 4"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/9bee03b258da3403d10ac323753ed1584853dc624f69a85d728a03ffcef1a8c8?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 5"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/a8abc5995448acd94a8284dbb23daed7bf9adf7d6fafe5c4acab06f43de8dded?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 6"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/e041d4719135af367e178c2228c7cd21b3ac0749ab972efa4ddf3da5ff73aa31?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 7"
    },
    {
        src: "https://cdn.builder.io/api/v1/image/assets/TEMP/1dd2a5dfc3412225d3f2c7bbab347ce8906703cb162c8e63881996500dca2e15?apiKey=e946ad7206da4373a899cf38e79aca51&",
        alt: "Image 8"
    },
];


const RecentWork: React.FC = () => {
    return (
        <section className="flex flex-col gap-10 items-center">
            <div id="work" className="w-full h-full container mx-auto flex">
                <h2 className="w-2/5 text-[2.5rem] text-[#0B0C0E] font-medium">
                    Recent Work
                </h2>
                <div className="w-full">
                    {workItems.map((item, index) => (
                        <WorkItem key={index} {...item} />
                    ))}
                </div>
            </div>
            <Carousel className="w-[90vw] h-[75vh]">
                {websiteImages.map((image, index) => (
                    <WebsiteCard key={index} src={image.src} alt={image.alt}/>
                ))}
            </Carousel>
        </section>
    );
};

export default RecentWork;
