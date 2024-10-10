import React from "react";
import Image from "next/image";
import ContactDetails from "./ContactDetails";
import SocialLinks from "./SocialLinks";

const ProfileSection: React.FC = () => {
    return (
        <section className="flex-1 flex flex-col items-start self-start">
            <div className="relative w-[20rem] h-[20rem]">
                <Image
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/f4e89ee0fb40d8ded6aa3de026904a219d4ccbe7b792f1c8adc0dcbab34cdcd0?apiKey=e946ad7206da4373a899cf38e79aca51&"
                    alt="Profile picture of Ayush Barnwal"
                    fill
                    className="object-cover rounded-full"
                />
            </div>
            <ContactDetails/>
            <SocialLinks/>
        </section>
    );
};

export default ProfileSection;
