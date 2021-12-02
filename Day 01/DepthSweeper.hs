depthSweeper :: [Integer] -> Int
depthSweeper input = sum $ zipWith (\x a -> if x > a then 0 else 1) input (tail input)