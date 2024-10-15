import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

export interface Emoji {
    name: string;
    category: string;
    group: string;
    htmlCode: string[];
    unicode: string[];
}       

export const emojiApi = createApi({
    reducerPath: "emojiApi",
    baseQuery: fetchBaseQuery({baseUrl: "https://emojihub-1001447344924.asia-southeast2.run.app/api/"}),
    endpoints: (builder) => ({
        getRandomEmoji: builder.query<Emoji, void>({
            query: () => `random`,
        }),
    }),
});
