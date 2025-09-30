import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAuth } from "../context/auth-context";
import ItemService from "../services/ItemService";
import CardComponent from "../components/CardComponent";
import ItemInfoComponent from "../components/ItemInfoComponent";

export default function CardPage() {
  const { itemId } = useParams(); // gets itemId from the URL
  const { user } = useAuth();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    ItemService.get(itemId)
      .then((data) => setItem(data))
      .catch((err) => console.error("Failed to fetch card:", err))
      .finally(() => setLoading(false));
  }, [itemId]);

  if (loading) return <p>Loading item...</p>;
  if (!item) return <p>Item not found.</p>;

  return (
    <div className="flex justify-center gap-30 m-5 bg-gray-100">
      <CardComponent card={item} isClickable={false}/>
      <ItemInfoComponent item={item} userId={user.id}/>
    </div>
  );
}
