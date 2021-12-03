-- Assuming input comes as [Integer]

-- Part 1
depthSweeper :: [Integer] -> Int
depthSweeper input = sum $ zipWith (\x a -> if x < a then 1 else 0) input (tail input)

-- Part 2
denoiseData :: [Integer] -> [Integer]
denoiseData (x:xs) = if length xs < 2 then [] else (x + xs!!0 + xs!!1) : denoiseData xs 

depthSweeperDenoised :: [Integer] -> Int
depthSweeperDenoised = depthSweeper . denoiseData
