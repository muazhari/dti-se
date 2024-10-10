import React from "react";
import ProfileSection from "./components/ProfileSection";
import ContactForm from "./components/ContactForm";

const Home: React.FC = () => {
    return (
        <div className="container my-[10rem] mx-auto flex justify-between gap-[5rem]">
            <ProfileSection/>
            <ContactForm/>
        </div>
    );
};

export default Home;
