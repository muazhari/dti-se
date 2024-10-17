import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

export interface User {
    name: string;
    email: string;
    expertise: string;
}

interface Result {
    name: {
        first: string;
        last: string;
    };
    email: string;
}

interface UserResponse {
    results: Result[];
}

export const userApi = createApi({
    reducerPath: "userApi",
    baseQuery: fetchBaseQuery({baseUrl: "https://randomuser.me/api"}),
    endpoints: (builder) => ({
        getRandomUser: builder.query<User[], { results: number }>({
            queryFn: async (args, api, extraOptions, baseQuery) => {
                const response = await baseQuery({url: `?results=${args.results}`});
                const responseData = response.data as UserResponse;
                const data = responseData.results.map((user) => ({
                    name: `${user.name.first} ${user.name.last}`,
                    email: user.email,
                    expertise: "Lorem ipsum dolor sit amet.",
                }));
                return {data};
            }
        }),
    })
});
