from token_containers.token_container import TokenContainer
from mill_observer import MillObserver


class Gameboard:
    "The board on which the game is played."

    def __init__(
        self,
        token_banks: list[TokenContainer],
        intersections: list[TokenContainer],
        mill_observers: list[MillObserver],
    ):
        self.token_banks = token_banks
        self.intersections = intersections
        self.mill_observers = mill_observers

    def __check_tokens_on_board(self, colour: str) -> int:
        "Get the number of tokens of a given colour remaining on the board."

        tokens_on_board = 0

        for intersection in self.intersections:
            if intersection.peek_token().get_colour == colour:
                tokens_on_board += 1

        return tokens_on_board

    def has_mill(self, colour: str) -> bool:
        "Ask the Gameboard's mill observers whether they've found a mill."

        for mill_observer in self.mill_observers:
            if mill_observer.has_mill(colour):
                return True
        return False

    def get_legal_token_origins(self, colour: str) -> list[TokenContainer]:
        "Get all of the containers from which tokens can be popped."

        for token_bank in self.token_banks:
            if (
                not token_bank.is_empty()
                and token_bank.peek_token().get_colour() == colour
            ):
                return [token_bank]
        return [
            intersection
            for intersection in self.intersections
            if intersection.peek_token().get_colour() == colour
        ]

    def get_legal_token_destinations(
        self, token_origin: TokenContainer, colour: str
    ) -> list[TokenContainer]:
        "Get all of the containers to which tokens can be pushed."

        if token_origin in self.token_banks:
            return [
                intersection
                for intersection in self.intersections
                if not intersection.is_full()
            ]
        elif (
            token_origin in self.intersections
            and self.__check_tokens_on_board(colour) > 3
        ):
            return [
                intersection
                for intersection in token_origin.get_destination_containers()
                if not intersection.is_full()
            ]
        elif (
            token_origin in self.intersections
            and self.__check_tokens_on_board(colour) == 3
        ):
            return [
                intersection
                for intersection in self.intersections
                if not intersection.is_full()
            ]
        else:
            return []

    def get_legal_token_removal_containers(
        self, colour_of_player_with_mill: str
    ) -> list[TokenContainer]:
        """Get all of the containers from which tokens can be removed from the
        game.
        """

        opponent_containers = [
            intersection
            for intersection in self.intersections
            if intersection.peek_token().get_colour() != colour_of_player_with_mill
        ]

        if len(opponent_containers) > 0:
            opponent_colour = opponent_containers[0].peek_token().get_colour()

        token_intersection_map = {
            intersection.peek_token(): intersection
            for intersection in opponent_containers
        }

        for mill_observer in self.mill_observers:
            if mill_observer.has_mill(opponent_colour):
                tokens = mill_observer.get_tokens()
                for token in tokens:
                    del token_intersection_map[token]

        legal_removal_containers = list[token_intersection_map.values()]
        return legal_removal_containers
