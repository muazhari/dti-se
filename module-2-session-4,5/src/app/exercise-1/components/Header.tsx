import React from "react";
import Link from "next/link";

const Header: React.FC = () => {
    return (
        <header className="z-10 py-[1.625rem] absolute top-0 left-0 right-0 z-1">
            <nav className="container mx-auto flex justify-between items-center">
                <div className="text-[1.125rem] text-[#0B0C0E] font-normal">
                    @Ayush Barnwal
                </div>
                <div className="flex gap-[2.5rem] text-[1.125rem] text-[#0B0C0E] font-normal">
                    <Link href="#about">About</Link>
                    <Link href="#work">Work</Link>
                    <Link href="#contact">Contact</Link>
                </div>
            </nav>
        </header>
    );
};

export default Header;
