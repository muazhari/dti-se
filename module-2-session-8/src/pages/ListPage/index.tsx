import Card from "../../components/Card";
import Header from "../../components/Header";
import MobileWrapper from "../../components/MobileWrapper";
import usePokemonList from "../../hooks/usePokemonList";
import SortAndViewComponent from "../../components/SortAndView";

const ListPage: React.FC = () => {
    const {data, isLoading, error, columnView} = usePokemonList();
    if (error) return <div>Something is wrong :( </div>
    return (
        <MobileWrapper>
            <Header withSearch/>
            <SortAndViewComponent/>
            {isLoading ? (
                <div>Loading...</div>
            ) : (
                <div className={`px-5 py-4 grid grid-cols-${columnView} gap-5`}>
                    {data.map((each, index) => <Card key={index} name={each.name}/>)}
                </div>
            )}
        </MobileWrapper>
    );
};

export default ListPage;
