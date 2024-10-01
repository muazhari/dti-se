/* eslint-disable @typescript-eslint/no-explicit-any */

import {pokemonApi} from "../app/api.ts";
import {useEffect} from "react";

const usePokemonDetails = (pokemonName: string) => {
    const {data, isLoading, error, refetch} = pokemonApi.useGetPokemonDetailsQuery(pokemonName);

    useEffect(() => {
        refetch();
    }, []);

    return {data, isLoading, error};
};

export default usePokemonDetails;
