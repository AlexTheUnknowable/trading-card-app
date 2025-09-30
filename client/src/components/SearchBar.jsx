export default function SearchBar({searchTerm, setSearchTerm}) {
    return (
        <input className="border-1 border-gray-500 rounded-sm placeholder:text-gray-500 placeholder:italic placeholder:p-1"
            name="search"
            type="text"
            placeholder="Search yippeeeee"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
        />
    );
}