export default function ItemBuyComponent() {
    const buy = () => {
        console.log("buy") // implement later
    }

    return (
        <>
        <p>This item is up for sale.</p>
        <button onClick={buy} className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300;">Buy</button>
        </>
    );
}