import React from "react";

const ContactDetails: React.FC = () => {
    return (
        <div className="flex flex-col mt-14">
            <h2 className="text-lg leading-relaxed text-neutral-700">
                Contact Details
            </h2>
            <div className="flex flex-col mt-2 text-3xl leading-snug text-neutral-950">
                <a href="mailto:ayush.barnwal@brightscout.com">
                    ayush.barnwal@brightscout.com
                </a>
                <a href="tel:+918651447521" className="mt-1.5">
                    +91 8651447521
                </a>
            </div>
        </div>
    );
};

export default ContactDetails;
