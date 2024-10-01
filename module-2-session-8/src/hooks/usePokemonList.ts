import {useDispatch, useSelector} from "react-redux";
import {Pokemon, pokemonSlice, PokemonState} from "../app/slice.ts";
import {RootState} from "../app/store.ts";
import {pokemonApi} from "../app/api.ts";
import {useEffect} from "react";

export interface PokemonContextType {
    data: Pokemon[];
    isLoading: boolean;
    error: unknown;
    sortByField: string;
    columnView: number;
    setSearchQuery: (query: string) => void;
    setSortByField: (query: string) => void;
    setColumnView: (column: number) => void;
}

const usePokemonList = () => {
    const dispatch = useDispatch();
    const pokemonState: PokemonState = useSelector((state: RootState) => state.pokemon);

    const {data, isLoading, error, refetch} = pokemonApi.useGetPokemonListQuery({
        query: pokemonState.searchQuery,
        sortBy: pokemonState.sortByField
    });

    useEffect(() => {
        refetch();
    }, []);

    const setSearchQuery = (query: string) => {
        dispatch(pokemonSlice.actions.setSearchQuery(query));
    }

    const setSortByField = (query: string) => {
        dispatch(pokemonSlice.actions.setSortByField(query));
    }

    const setColumnView = (column: number) => {
        dispatch(pokemonSlice.actions.setColumnView(column));
    }

    const value: PokemonContextType = {
        data,
        isLoading,
        error,
        sortByField: pokemonState.sortByField,
        columnView: pokemonState.columnView,
        setSearchQuery,
        setSortByField,
        setColumnView
    };

    return value
};

export default usePokemonList;
