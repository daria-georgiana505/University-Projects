import numpy as np
# import sympy as sy
import matplotlib.pyplot as plt

plt.rcParams["figure.figsize"] = (12,7.3)

"""
Fenesan Daria Georgiana - Group 913
"""

def f(x, y, b):
    return ((x**2)+b*(y**2))/2

def gradient(x, y, b):
    return x, b*y

def get_next_x_and_y(xk, yk, b, s): #(xk+1,yk+1)=(xk,yk)−s∇f(xk,yk)
    return xk-(s*xk), yk-(s*yk*b)

def get_step_size_for_b_1_over_2(xk, yk):
    return (2*(xk**2)+(1/2)*(yk**2))/(2*(xk**2)+(1/4)*(yk**2))

def get_step_size_for_b_1_over_5(xk, yk):
    return (2*(xk**2)+(2/25)*(yk**2))/(2*(xk**2)+(2/125)*(yk**2))

def get_step_size_for_b_1_over_10(xk, yk):
    return (2*(xk**2)+(1/50)*(yk**2))/(2*(xk**2)+(1/500)*(yk**2))

if __name__ == '__main__':
    fig = plt.figure()
    ax1 = fig.add_subplot(221, projection='3d')
    ax2 = fig.add_subplot(222, projection='3d')
    ax3 = fig.add_subplot(223, projection='3d')
    ax4 = fig.add_subplot(224, projection='3d')

    xline = np.arange(-10, 11, 1)
    yline = np.arange(-10, 11, 1)
    zline = f(xline,yline,1)

    Xline, Yline = np.meshgrid(xline, yline)

    # FIRST PLOT: b = 1
    Zline = f(Xline, Yline, 1)
    ax1.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='viridis', edgecolor='none', alpha=0.7)
    s = 1 # step size for b = 1 is 1
    X, Y, Z = [], [], []
    # starting point for gradient descent
    X.append(-5)
    Y.append(8)
    Z.append(f(-5, 8, 1))
    nr_max_steps = 20
    while gradient(X[-1], Y[-1], 1)!=(0,0) and nr_max_steps>0:
        next_x, next_y = get_next_x_and_y(X[-1], Y[-1], 1, s)
        next_z = f(next_x, next_y, 1)
        X.append(next_x)
        Y.append(next_y)
        Z.append(next_z)
        nr_max_steps -= 1
    ax1.plot(X, Y, Z, color='black', marker='o', linestyle='dashed')

    # SECOND PLOT: b = 1/2
    Zline = f(Xline, Yline, 1 / 2)
    ax2.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='viridis', edgecolor='none', alpha=0.7)
    X, Y, Z = [], [], []
    # starting point for gradient descent
    X.append(-5)
    Y.append(8)
    Z.append(f(-5, 8, 1 / 2))
    s = get_step_size_for_b_1_over_2(X[-1], Y[-1])
    nr_max_steps = 20
    while gradient(X[-1], Y[-1], 1 / 2) != (0, 0) and nr_max_steps > 0:
        next_x, next_y = get_next_x_and_y(X[-1], Y[-1], 1 / 2, s)
        next_z = f(next_x, next_y, 1 / 2)
        X.append(next_x)
        Y.append(next_y)
        Z.append(next_z)
        s = get_step_size_for_b_1_over_2(X[-1], Y[-1])
        nr_max_steps -= 1
    ax2.plot(X, Y, Z, color='black', marker='o', linestyle='dashed')

    # THIRD PLOT: b = 1/5
    Zline = f(Xline, Yline, 1 / 5)
    ax3.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='viridis', edgecolor='none', alpha=0.7)
    X, Y, Z = [], [], []
    # starting point for gradient descent
    X.append(-5)
    Y.append(8)
    Z.append(f(-5, 8, 1 / 5))
    s = get_step_size_for_b_1_over_5(X[-1], Y[-1])
    nr_max_steps = 20
    while gradient(X[-1], Y[-1], 1 / 5) != (0, 0) and nr_max_steps > 0:
        next_x, next_y = get_next_x_and_y(X[-1], Y[-1], 1 / 5, s)
        next_z = f(next_x, next_y, 1 / 5)
        X.append(next_x)
        Y.append(next_y)
        Z.append(next_z)
        s = get_step_size_for_b_1_over_5(X[-1], Y[-1])
        nr_max_steps -= 1
    ax3.plot(X, Y, Z, color='black', marker='o', linestyle='dashed')

    # FOURTH PLOT: b = 1/10
    Zline = f(Xline, Yline, 1 / 10)
    ax4.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='viridis', edgecolor='none', alpha=0.7)
    X, Y, Z = [], [], []
    # starting point for gradient descent
    X.append(-5)
    Y.append(8)
    Z.append(f(-5, 8, 1 / 10))
    s = get_step_size_for_b_1_over_10(X[-1], Y[-1])
    nr_max_steps = 20
    while gradient(X[-1], Y[-1], 1 / 10) != (0, 0) and nr_max_steps > 0:
        next_x, next_y = get_next_x_and_y(X[-1], Y[-1], 1 / 10, s)
        next_z = f(next_x, next_y, 1 / 10)
        X.append(next_x)
        Y.append(next_y)
        Z.append(next_z)
        s = get_step_size_for_b_1_over_10(X[-1], Y[-1])
        nr_max_steps -= 1
    ax4.plot(X, Y, Z, color='black', marker='o', linestyle='dashed')

    plt.show()



