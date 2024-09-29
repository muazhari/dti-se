'use client'
import React from "react";

import Image from "next/image";
import {useDispatch, useSelector} from "react-redux";
import {landingSlice} from "@/app/exercise-1/stores/slice";

const Hero: React.FC = () => {
    const landingState: ReturnType<typeof landingSlice.getInitialState> = useSelector((state) => state.landing);
    const dispatch = useDispatch();

    const handleSideButtonClick = React.useCallback((event: React.MouseEvent<HTMLButtonElement>) => {
        dispatch(landingSlice.actions.switchSidebar());
    }, [dispatch, landingState])

    return (
        <section className="bg-[#C7D0D9] h-[100vh] flex items-end">
            <button
                className={`z-20 self-center group absolute right-0 ${landingState.isShowSidebar && 'hidden'}`}
                onClick={handleSideButtonClick}
            >
                <div
                    className="bg-[#0B0C0E] text-[3.5rem] text-white font-medium uppercase rounded-l-full py-[0.5rem] px-[1.875rem] group-hover:hidden">
                    👋
                </div>
                <div
                    className="bg-[#0B0C0E] text-[3.5rem] text-white font-medium uppercase rounded-l-full py-[0.5rem] px-[1.875rem] hidden group-hover:block">
                    {`👋 Hi I'm Ayush`}
                </div>
            </button>
            <div className="absolute w-full h-full">
                <Image
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/80aca98b8a0e3cd6fec42956b691cc65c1fb85b89df58fcbaec1f5b4e13d04be?apiKey=e946ad7206da4373a899cf38e79aca51&"
                    alt=""
                    fill
                    className="object-contain"
                />
            </div>
            <h1 className="z-10 flex-1 flex-nowrap text-center font-medium text-white text-[10rem] text-nowrap overflow-hidden">
                Webflow Developer - UI/UX Designer
            </h1>
        </section>
    );
};

export default Hero;
