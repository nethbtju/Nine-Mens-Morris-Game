from token_containers.token_container import TokenContainer
from gameboard import Gameboard


class Player:
    "A player in the game."

    def __init__(self, colour: str, gameboard: Gameboard):
        self.colour = colour
        self.gameboard = gameboard

    def play_turn(self):
        "Play a turn in the game."

        token_origin = self.__choose_token_origin()
        token = token_origin.pop_token()
        legal_destinations = token_origin.get_destination_containers()
        token_destination = self.__choose_token_destination(legal_destinations)
        token_destination.push_token(token)
        if self.gameboard.has_mill(self.colour):
            self.__play_mill_turn()

    def __play_mill_turn(self):
        "Play a mill turn in the game."

        container_to_remove_token_from = self.__choose_token_removal()
        container_to_remove_token_from.pop_token()

    def __choose_token_origin(self) -> TokenContainer:
        "Choose which token to move."

        legal_token_origins = self.gameboard.get_legal_token_origins(self.colour)
        location_container_pairs = {}

        location_container_pairs = {
            legal_token_origin.get_location(): legal_token_origin
            for legal_token_origin in legal_token_origins
        }

        print(location_container_pairs.keys())
        chosen_origin = input("Choose a location to gather a token from.")
        return location_container_pairs[chosen_origin]

    def __choose_token_destination(
        self, token_origin: TokenContainer
    ) -> TokenContainer:
        "Choose where to move a token to."

        legal_token_destinations = self.gameboard.get_legal_token_destinations(
            token_origin, self.colour
        )

        location_container_pairs = {
            legal_token_destination.get_location(): legal_token_destination
            for legal_token_destination in legal_token_destinations
        }

        print(location_container_pairs.keys())
        chosen_destination = input("Choose a location to move a token to.")
        return location_container_pairs[chosen_destination]

    def __choose_token_removal(self) -> TokenContainer:
        "Choose a container to remove a token from."

        legal_token_removal_containers = (
            self.gameboard.get_legal_token_removal_containers(self.colour)
        )

        location_container_pairs = {
            legal_token_removal_container.get_location(): legal_token_removal_container
            for legal_token_removal_container in legal_token_removal_containers
        }

        print(location_container_pairs.keys())
        chosen_removal = input("Choose a location to remove a token from.")
        return location_container_pairs[chosen_removal]
