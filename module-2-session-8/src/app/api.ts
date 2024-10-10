import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react';

export interface Pokemon {
    name: string;
    url: string;
}

export interface PokemonDetails {
    name: string;
    id: number;
    health: number;
    attack: number;
    defense: number;
    spriteFront: string;
    artworkFront: string;
}

export const pokemonApi = createApi({
    reducerPath: 'pokemonApi',
    baseQuery: fetchBaseQuery({baseUrl: 'https://pokeapi.co/api/v2/'}),
    endpoints: (builder) => ({
        getPokemonDetails: builder.query<PokemonDetails, string>({
            query: (pokemonName) => `pokemon/${pokemonName}`,
            transformResponse: (response: never) => {
                const data = response;
                const spriteFront = data.sprites.front_default;
                const artworkFront = data.sprites.other['official-artwork'].front_default;
                const {name, id} = data;
                const health = data.stats.find((stat: never) => stat.stat.name === 'hp').base_stat;
                const attack = data.stats.find((stat: never) => stat.stat.name === 'attack').base_stat;
                const defense = data.stats.find((stat: never) => stat.stat.name === 'defense').base_stat;
                const stats = data.stats;

                return {stats, name, id, health, attack, defense, spriteFront, artworkFront};
            }
        }),
        getPokemonList: builder.query<Pokemon[], { query?: string, sortBy?: string }>({
            queryFn: async ({query, sortBy}, queryApi, extraOptions, fetchWithBQ) => {
                const results = await fetchWithBQ({url: `pokemon?limit=${query ? 10000 : 20}`});
                let filteredResults: Pokemon[] = results.data.results;

                if (query) {
                    const regex = new RegExp(query, 'i');
                    filteredResults = filteredResults.filter(pokemon => regex.test(pokemon.name));
                }

                if (sortBy === 'hp') { // Assuming sortBy is for 'hp' only for now
                    const detailsMap = await Promise.all(filteredResults.map(async pokemon => {
                        const result = await queryApi.dispatch(pokemonApi.endpoints.getPokemonDetails.initiate(pokemon.name));
                        return result.data;
                    }));
                    console.log(detailsMap);

                    filteredResults = detailsMap
                        .sort((a, b) => a.stats.find(stat => stat.stat.name === 'hp').base_stat - b.stats.find(stat => stat.stat.name === 'hp').base_stat)
                        .map(each => ({name: each.name, url: each.url})); // Keep only name and url
                }
                filteredResults = filteredResults.slice(0, 20);

                return {data: filteredResults};
            },
        }),
    }),
});
