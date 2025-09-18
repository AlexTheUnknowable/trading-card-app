import type { Card } from "../services/cardService";

export default function CardComponent({ card }: { card: Card }) {
  return (
    <div className="bg-sky-500 hover:bg-sky-700">
    <p>{card.name}</p>
    </div>
  );
}