import React from "react";
import FormInput from "./FormInput";

const ContactForm: React.FC = () => {
    return (
        <form className="flex-1 flex flex-col mt-3.5 ">
            <h1 className="self-start text-6xl font-medium text-neutral-950">
                {`Let's build something cool together`}
            </h1>
            <div className="flex flex-col mt-9">
                <FormInput label="Name" placeholder="James Robert"/>
                <FormInput
                    label="Email"
                    placeholder="ayush.barnwal@brightscout.com"
                    type="email"
                />
                <FormInput label="Subject" placeholder="For web design work Enquire"/>
                <FormInput label="Message" placeholder="Type your Message" isTextArea/>
                <button
                    type="submit"
                    className="px-12 py-6 mt-8 text-lg font-medium whitespace-nowrap bg-neutral-950 rounded-[18rem] text-slate-100 w-[14rem]"
                >
                    Submit
                </button>
            </div>
        </form>
    );
};

export default ContactForm;
