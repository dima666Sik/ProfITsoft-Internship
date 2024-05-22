const PlanetItem = ({ planet }) => {
    const { name, hasRings, hasMoons, atmosphericComposition } = planet;
    return (
        <li>
            <h3>{name}</h3>
            <p>Має кільця: {hasRings ? "Так" : "Ні"}</p>
            <p>Має місяці: {hasMoons ? "Так" : "Ні"}</p>
            <p>Склад атмосфери: {atmosphericComposition}</p>
        </li>
    );
};

export default PlanetItem;