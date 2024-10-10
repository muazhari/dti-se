import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

export interface ContactMail {
    name: string;
    email: string;
    subject: string;
    message: string;
}

export const contactApi = createApi({
    reducerPath: "contactApi",
    baseQuery: fetchBaseQuery({baseUrl: "https://json-server-trial.vercel.app/"}),
    endpoints: (builder) => ({
        createMail: builder.mutation<unknown, ContactMail>({
            queryFn: async (args, queryApi, extraOptions, baseQuery) => {
                return baseQuery({
                    url: "/mails",
                    method: "POST",
                    body: args,
                });
            }
        }),
    }),
});
