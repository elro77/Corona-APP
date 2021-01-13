import numpy as np
def to_prob(x):
    # return 1 / (1 + np.exp(-x))
    return np.minimum(np.sqrt(x),1)

def scale_feature(input_val, max_val, min_val):
    scaled = np.minimum(np.maximum((input_val-min_val)/(max_val - min_val),0.0),1.0)
    return to_prob(scaled)


