import { useAuth } from "../context/auth-context";
import CardService from "../services/CardService";
import CardList from "../components/CardList";

export default function Home() {
  const { user, token } = useAuth();

  return (
    <CardList
      fetchData={CardService.list}
      linkFunction={(card) => `/cards/${card.id}`}
      titleBlurb={<>
      <p>This is the home page.</p>
      <p>{token ? `Welcome, ${user.username}!` : "You are not logged in."}</p>
      </>}
    />
  );
}