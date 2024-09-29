'use client'
import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {landingSlice} from "@/app/exercise-1/stores/slice";
import Link from "next/link";
import Image from "next/image";


const Sidebar: React.FC = () => {
    const navItems = ["Home", "About", "Work", "Contact"];
    const socialLinks = ["Linkedin", "Twitter", "Instagram", "Webflow"];

    const landingState: ReturnType<typeof landingSlice.getInitialState> = useSelector((state) => state.landing);
    const dispatch = useDispatch();

    const handleSideButtonClick = React.useCallback((event: React.MouseEvent<HTMLButtonElement>) => {
        dispatch(landingSlice.actions.switchSidebar());
    }, [dispatch, landingState])

    return (
        <div
            className={`${!landingState.isShowSidebar && 'hidden'} w-[100vw] h-[100vh] z-30 fixed top-0 bottom-0 right-0 left-0 flex`}
        >
            <div className="w-1/2 bg-opacity-50 bg-neutral-950"/>
            <div className="w-1/2 flex flex-col bg-neutral-950 text-white p-10">
                <button
                    className="self-end relative w-[3.6rem] h-[3.6rem]"
                    onClick={handleSideButtonClick}
                >
                    <Image
                        src="https://cdn.builder.io/api/v1/image/assets/TEMP/11df7af740ee1a08220ab50d4c7d43ab0900e6470e8bb6143aeb430456d06197?apiKey=e946ad7206da4373a899cf38e79aca51"
                        alt=""
                        fill
                        className="object-cover rounded-full"
                    />
                </button>
                <div className="pl-6 flex flex-col">
                    <div className="flex gap-5 flex-col self-start text-5xl font-medium">
                        {navItems.map((value, index) => (
                            <Link href={`#${value.toLowerCase()}`} key={index} className="mt-1.75rem">{value}</Link>
                        ))}
                    </div>
                </div>
                <div
                    className="pl-6 mt-10 flex gap-12 items-start text-lg">
                    {socialLinks.map((value, index) => (
                        <Link href={`#${value.toLowerCase()}`} key={index}>{value}</Link>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Sidebar;
