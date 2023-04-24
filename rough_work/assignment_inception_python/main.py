from game import GameEngine.GameEngine
from gameboard import Gameboard
from game_token import GameToken
from mill_observer import MillObserver
from player import Player
from token_containers.observed_token_container import ObservedTokenContainer
from token_containers.token_container import TokenContainer


def main():
    "Wire together objects and run game."

    # Outer intersections only
    outer_top_left = ObservedTokenContainer("Outer top left", 1)
    outer_top_centre = ObservedTokenContainer("Outer top centre", 1)
    outer_top_right = ObservedTokenContainer("Outer top right", 1)
    outer_left_centre = ObservedTokenContainer("Outer left centre", 1)
    outer_bottom_left = ObservedTokenContainer("Outer bottom left", 1)
    outer_bottom_centre = ObservedTokenContainer("Outer bottom centre", 1)
    outer_bottom_right = ObservedTokenContainer("Outer bottom right", 1)
    outer_right_centre = ObservedTokenContainer("Outer right centre", 1)

    outer_top_left.add_destination_container(outer_top_centre)
    outer_top_left.add_destination_container(outer_left_centre)

    outer_top_centre.add_destination_container(outer_top_left)
    outer_top_centre.add_destination_container(outer_top_right)

    outer_top_right.add_destination_container(outer_top_centre)
    outer_top_right.add_destination_container(outer_right_centre)

    outer_right_centre.add_destination_container(outer_top_right)
    outer_right_centre.add_destination_container(outer_bottom_right)

    outer_bottom_right.add_destination_container(outer_right_centre)
    outer_bottom_right.add_destination_container(outer_bottom_centre)

    outer_bottom_centre.add_destination_container(outer_bottom_right)
    outer_bottom_centre.add_destination_container(outer_bottom_left)

    outer_bottom_left.add_destination_container(outer_bottom_centre)
    outer_bottom_left.add_destination_container(outer_left_centre)

    outer_left_centre.add_destination_container(outer_top_left)
    outer_left_centre.add_destination_container(outer_bottom_left)

    intersections = [
        outer_top_left,
        outer_top_centre,
        outer_top_right,
        outer_left_centre,
        outer_bottom_left,
        outer_bottom_centre,
        outer_bottom_right,
        outer_right_centre,
    ]

    outer_top_observer = MillObserver()
    outer_top_left.attach_observer(outer_top_observer)
    outer_top_centre.attach_observer(outer_top_observer)
    outer_top_right.attach_observer(outer_top_observer)

    outer_left_observer = MillObserver()
    outer_top_left.attach_observer(outer_left_observer)
    outer_left_centre.attach_observer(outer_left_observer)
    outer_bottom_left.attach_observer(outer_left_observer)

    outer_bottom_observer = MillObserver()
    outer_bottom_left.attach_observer(outer_bottom_observer)
    outer_bottom_centre.attach_observer(outer_bottom_observer)
    outer_bottom_right.attach_observer(outer_bottom_observer)

    outer_right_observer = MillObserver()
    outer_top_right.attach_observer(outer_right_observer)
    outer_right_centre.attach_observer(outer_right_observer)
    outer_bottom_right.attach_observer(outer_right_observer)

    mill_observers = [
        outer_top_observer,
        outer_left_observer,
        outer_bottom_observer,
        outer_right_observer,
    ]

    maximum_tokens_in_bank = 9

    white_token_bank = TokenContainer("Bank", maximum_tokens_in_bank)
    black_token_bank = TokenContainer("Bank", maximum_tokens_in_bank)

    while not white_token_bank.is_full():
        token = GameToken("white")
        white_token_bank.push_token(token)

    while not black_token_bank.is_full():
        token = GameToken("black")
        black_token_bank.push_token(token)

    token_banks = [white_token_bank, black_token_bank]

    for intersection in intersections:
        white_token_bank.add_destination_container(intersection)
        black_token_bank.add_destination_container(intersection)

    gameboard = Gameboard(token_banks, intersections, mill_observers)

    player_one = Player("white", gameboard)
    player_two = Player("black", gameboard)

    game = GameEngine.GameEngine([player_one, player_two])
    game.run()


if __name__ == "__main__":
    main()
