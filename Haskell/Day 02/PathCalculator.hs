data Direction = Forward Int | Up Int | Down Int deriving (Eq, Show)

-- Assuming input comes as [Direction]

-- Part 1
calculatePathProductSimple :: [Direction] -> Int
calculatePathProductSimple input = (sum [x | (Forward x) <- input]) * ((sum [x | (Down x) <- input]) - (sum [x | (Up x) <- input]))

-- Part 2's ghost
--calculatePathAdvanced :: [Direction] -> Int -> (Int,Int)
--calculatePathAdvanced ((Forward x):xs) aim =  ()
--calculatePathAdvanced ((Up x):xs)      aim =  
--calculatePathAdvanced ((Down x):xs)    aim =  
--calculatePathAdvanced     _             _  = (0,0)
