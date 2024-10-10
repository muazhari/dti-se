import React from "react";

const Footer: React.FC = () => {
    return (
        <footer className="bg-[#0B0C0E] text-[#C7D0D9] py-[3rem]">
            <div className="container mx-auto flex justify-between items-center">
                <p className="text-[1.125rem]">Build with 💖 by Brightscout & Ayush</p>
                <nav>
                    <ul className="flex gap-[2rem]">
                        <li>
                            <a href="#" className="text-[#F4F7FA]">
                                Linkedin
                            </a>
                        </li>
                        <li>
                            <a href="#">Twitter</a>
                        </li>
                        <li>
                            <a href="#">Instagram</a>
                        </li>
                        <li>
                            <a href="#">Webflow</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </footer>
    );
};

export default Footer;
