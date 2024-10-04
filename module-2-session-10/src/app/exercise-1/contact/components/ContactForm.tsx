'use client'
import React from "react";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";

interface FormInputProps {
    name: string;
    label: string;
    placeholder: string;
    type?: string;
    isTextArea?: boolean;
}

const FormInput: React.FC<FormInputProps> = (
    {
        name,
        label,
        placeholder,
        as = "input",
    }
) => {
    const inputId = `${label.toLowerCase()}-input`;

    return (
        <div className="flex flex-col mt-8">
            <label htmlFor={inputId} className="text-lg leading-relaxed text-neutral-950">
                {label}
            </label>
            <Field
                as={as}
                id={inputId}
                name={name}
                placeholder={placeholder}
                className="mt-3 text-3xl leading-snug opacity-60 text-neutral-700 bg-transparent resize-none"
                rows={as === "textarea" ? 4 : undefined}
            />
            <ErrorMessage name={name} component="div" className="text-red-500 text-sm mt-1"/>
            <div className="mt-3 border border-solid bg-neutral-950 border-neutral-950 min-h-[0.1rem]"/>
        </div>
    );
};

const ContactForm: React.FC = () => {
    const initialValues = {
        name: "",
        email: "",
        subject: "",
        message: "",
    };

    const validationSchema = Yup.object().shape({
        name: Yup.string().required("Name is required"),
        email: Yup.string().email("Invalid email").required("Email is required"),
        subject: Yup.string().required("Subject is required"),
        message: Yup.string().required("Message is required"),
    });

    const handleSubmit = (values: typeof initialValues) => {
        alert(JSON.stringify(values, null, 2));
    };

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
        >
            <Form className="flex-1 flex flex-col mt-3.5">
                <h1 className="self-start text-6xl font-medium text-neutral-950">
                    {`Let's build something cool together`}
                </h1>
                <div className="flex flex-col mt-9">
                    <FormInput name="name" label="Name" placeholder="James Robert"/>
                    <FormInput
                        name="email"
                        label="Email"
                        placeholder="ayush.barnwal@brightscout.com"
                        type="email"
                    />
                    <FormInput name="subject" label="Subject" placeholder="For web design work Enquire"/>
                    <FormInput name="message" label="Message" placeholder="Type your Message" as="textarea"/>
                    <button
                        type="submit"
                        className="px-12 py-6 mt-8 text-lg font-medium whitespace-nowrap bg-neutral-950 rounded-[18rem] text-slate-100 w-[14rem]"
                    >
                        Submit
                    </button>
                </div>
            </Form>
        </Formik>
    );
};

export default ContactForm;
