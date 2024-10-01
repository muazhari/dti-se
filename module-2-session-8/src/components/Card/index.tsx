import {useCallback} from "react";
import usePokemonDetails from "../../hooks/usePokemonDetail";
import {useNavigate} from "react-router-dom";

interface CardProps {
    name: string,
    key?: number
}

const Card: React.FC<CardProps> = ({name, key}) => {
    const {data, isLoading, error} = usePokemonDetails(name);
    const navigate = useNavigate();

    const handleClickCard = useCallback(() => {
        navigate(`/detail/${name}`);
    }, [name, navigate]);

    if (isLoading || !data) return <div>Loading...</div>
    if (error) return <div>Something is wrong</div>
    return (
        <div key={key} onClick={handleClickCard} className="w-full pt-10 pb-2 px-5 bg-brilliant-white rounded-2xl">
            <img src={data.artworkFront} alt="pokemon"/>
            <div className="text-sm font-bold leading-[14px] mt-2 text-center capitalize">{data.name}</div>
        </div>
    );
};

export default Card;
