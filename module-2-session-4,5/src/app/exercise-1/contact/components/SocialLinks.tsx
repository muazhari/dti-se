import React from "react";

const socialLinks = [
    {name: "Linkedin", url: "#"},
    {name: "Instagram", url: "#"},
    {name: "Twitter", url: "#"},
    {name: "Webflow", url: "#"},
    {name: "Figma", url: "#"},
];

const SocialLinks: React.FC = () => {
    return (
        <div className="flex flex-col mt-10 whitespace-nowrap">
            <h2 className="text-lg leading-relaxed text-neutral-700">Social</h2>
            <div className="flex flex-col mt-2 text-3xl leading-snug text-neutral-950">
                {socialLinks.map((link, index) => (
                    <a
                        key={link.name}
                        href={link.url}
                        className={index > 0 ? "mt-1.5" : ""}
                    >
                        {link.name}
                    </a>
                ))}
            </div>
        </div>
    );
};

export default SocialLinks;
