import type { Route } from "./+types/cardView";
import cardService from "../services/cardService";

export async function loader({ params }: Route.LoaderArgs) {
  return cardService.get(params.cardId);
}

export default function CardView({
  loaderData,
}: Route.ComponentProps) {
    const card = loaderData;
    return (
        <div>
            {card.name}
        </div>
    );
}