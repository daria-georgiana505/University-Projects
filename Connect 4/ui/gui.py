import copy
import os
import sys

from exceptions import exceptions
from services.services import Services
import pygame


class GUI:
    # width, height = 0, 0

    def __init__(self):
        self.__game = Services()
        pygame.init()
        width, height = pygame.display.Info().current_w, pygame.display.Info().current_h
        pygame.display.set_caption('CONNECT 4')
        os.environ['SDL_VIDEO_WINDOW_POS'] = "%d,%d" % (width // 2 - 350, height // 2 - 350) #"420,80"

    def start_game(self):
        # self.print_initial_board()
        self.print_board()
        # turn = 0 # ADDED!!!
        turn = 1
        while self.__game.game_not_over():
            if turn % 2 == 0:
                self.bot_moves()
                turn += 1
            turn += self.player_moves()

    def check_for_winner(self):
        window = pygame.display.set_mode((700, 700))
        self.print_board()
        black = (0, 0, 0) #(255, 255, 0)
        myfont = pygame.font.SysFont(None, 75) #("arial", 40) #("monospace", 40)

        if self.__game.check_if_player_wins():
            label = myfont.render("Player wins!", 1, black)
            w,h = label.get_size()
            window.blit(label, (350 - w//2, 50 - h//2)) # window.blit(label, (250, 25))
            pygame.display.update()
            # print("Player wins!")
            pygame.time.wait(5000)
            sys.exit()
            # exit()
        elif self.__game.check_if_bot_wins():
            label = myfont.render("Bot wins!", 1, black)
            w, h = label.get_size()
            window.blit(label, (350 - w // 2, 50 - h // 2)) # window.blit(label, (250, 25))
            pygame.display.update()
            # print("Bot wins!")
            pygame.time.wait(5000)
            sys.exit()
            # exit()
        elif self.__game.check_if_draw():
            label = myfont.render("Draw!", 1, black)
            w, h = label.get_size()
            window.blit(label, (350 - w // 2, 50 - h // 2)) #window.blit(label, (250, 25))
            pygame.display.update()
            # print("Draw!")
            pygame.time.wait(5000)
            sys.exit()
            # exit()



    def print_board(self):
        # pygame.init()
        # pygame.display.set_caption('CONNECT 4')
        # screen = pygame.display.Info()
        window = pygame.display.set_mode((700, 700))
        white = (255, 255, 255)
        window.fill(white)

        square_size = 100
        radius = int(square_size / 2 - 10)
        blue = (0, 0, 255)
        black = (0, 0, 0)
        red = (255, 0, 0)
        yellow = (255, 255, 0)

        for row in range(6):
            for col in range(7):
                pygame.draw.rect(window, black, (col * square_size, 100 + row * square_size, square_size, square_size), 2)
                pygame.draw.circle(window, black, (col * square_size + square_size // 2, 100 + row * square_size + square_size // 2), radius)

        l = copy.deepcopy(self.__game.return_board())
        for row in range(6):
            for col in range(7):
                if l[row][col] == 1:
                    pygame.draw.circle(window, blue, (col * square_size + square_size // 2, 100 + row * square_size + square_size // 2), radius)
                if l[row][col] == 2:
                    pygame.draw.circle(window, red, (col * square_size + square_size // 2, 100 + row * square_size + square_size // 2), radius)

        pygame.display.flip() #pygame.display.update()


    # def insert_choice_player(self, col):
    #     try:
    #         self.__game.player_selects_choice(col)
    #         # self.print_board()
    #         # self.check_for_winner()
    #     except exceptions.CannotSelectThatColumn:
    #         print("Can't insert here!")
    #         col = int(input("> "))
    #         self.insert_choice_player(col)
    #     else:
    #         pass

    def player_moves(self):
        # pygame.init()
        # screen = pygame.display.set_mode((700, 700))
        # white = (255, 255, 255)
        # screen.fill(white)

        # square_size = 100
        # radius = int(square_size / 2 - 10)
        # blue = (0, 0, 255)
        # black = (0, 0, 0)
        # red = (255, 0, 0)
        # yellow = (255, 255, 0)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                sys.exit()

            # if event.type == pygame.WINDOWMINIMIZED:
            #     self.print_board()

            # if event.type == pygame.WINDOWMAXIMIZED:
            #     self.print_board()

            if event.type == pygame.WINDOWEXPOSED:
                self.print_board()

            # if event.type == pygame.WINDOWHIDDEN:
            #     self.print_board()

            # if event.type == pygame.WINDOWSHOWN:
            #     self.print_board()

            # if event.type == pygame.WINDOWCLOSE:
            #     self.print_board()

            if event.type == pygame.MOUSEBUTTONUP:
                pos = pygame.mouse.get_pos()
                col = pos[0] // 100

                # self.insert_choice_player(col)
                try:
                    self.__game.player_selects_choice(col)
                    # self.print_board()
                    self.check_for_winner()
                    return 1
                except exceptions.CannotSelectThatColumn:
                    pass
                else:
                    pass
        return 0

    def bot_moves(self):
        pygame.time.wait(400)
        self.__game.bot_selects_choice()
        # self.print_board()
        self.check_for_winner()

# if __name__ == '__main__':
#     m = GUI()
#
#     # m.print_board()
#     m.start_game()