'use client'
import React from "react";
import Hero from "./components/Hero";
import About from "./components/About";
import RecentWork from "./components/RecentWork";
import Testimonials from "./components/Testimonials";
import Contact from "./components/Contact";
import Image from "next/image";
import {landingSlice} from "./stores/slice";
import {useDispatch, useSelector} from "react-redux";
import Sidebar from "./components/Sidebar";

const Home: React.FC = () => {
    const landingState: ReturnType<typeof landingSlice.getInitialState> = useSelector((state) => state.landing);
    const dispatch = useDispatch();

    const handleBurgerButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        dispatch(landingSlice.actions.switchSidebar());
    }

    return (
        <div className="flex flex-col">
            <Sidebar/>
            <Hero/>
            <button
                className={`${landingState.isShowSidebar && 'hidden'} self-end mt-12 mr-12 top-12 right-12 sticky z-50 w-[5.375rem] h-[5.375rem]`}
                onClick={handleBurgerButtonClick}
            >
                <Image
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/e48a0c522d0c0222cab9f15f42f34e7f51ce4a5dc126d6e69fadb0b6354d12ee?apiKey=e946ad7206da4373a899cf38e79aca51&"
                    alt="hamburger"
                    fill
                    className="object-cover"
                />
            </button>
            <About/>
            <RecentWork/>
            <Testimonials/>
            <Contact/>
        </div>
    );
};

export default Home;
